package com.uncg.gameandgo;

import com.uncg.gameandgo.schema.ResponseWrapper;
import com.uncg.gameandgo.schema.NewUserRequest;
import com.uncg.gameandgo.schema.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private UserRepository userRepository;


	// TODO: Decide on how response will look.
	@ResponseBody
	@RequestMapping("/signup")
	public ResponseWrapper signup(@RequestBody NewUserRequest request)
	{
		User user = userRepository.create(request);
		if (user == null) {
			return new ResponseWrapper("username '" + request.getUsername() + "' was taken.");
		}
		return new ResponseWrapper(user + " was created");
	}
}
