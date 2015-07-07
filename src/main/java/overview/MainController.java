package overview;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainController {
	
	volatile static Integer requests = 0;

    @RequestMapping("/")
    @SuppressWarnings("unchecked")
    public String main(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) throws Exception {
        requests++;
        

		HashMap<String,Object> result =
                new ObjectMapper().readValue(System.getenv("VCAP_APPLICATION"), HashMap.class);
        
        model.addAllAttributes(result);
        model.addAttribute("mem", ((Map<String,Object>)result.get("limits")).get("mem"));
        model.addAttribute("disk", ((Map<String,Object>)result.get("limits")).get("disk"));
        model.addAttribute("requests", requests);
    	model.addAttribute("ipAddress", InetAddress.getLocalHost().getHostAddress());
    	
    	System.out.println(System.getenv("VCAP_SERVICES"));
    	
    	
        return "main";
    }
    
    @RequestMapping("/killSwitch")
    public void kill() {
    	System.exit(-1);
    }

}
