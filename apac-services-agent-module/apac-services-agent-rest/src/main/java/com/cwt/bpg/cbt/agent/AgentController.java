package com.cwt.bpg.cbt.agent;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.agent.model.AgentInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Agent")
public class AgentController {

	@Autowired
	private AgentService agentService;

	@GetMapping(
			path = "/agent", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns list of all agents.")
	public ResponseEntity<List<AgentInfo>> getAgents() {
		return new ResponseEntity<>(agentService.getAgents(), HttpStatus.OK);
	}

	@GetMapping(
			path = "/agent/{uid}/{countryCode}", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[HK/SG only] Returns agent based on a [uid | countryCode] combination.")
	public ResponseEntity<AgentInfo> getAgent(@PathVariable("uid") @ApiParam("uid") String uid, 
											  @PathVariable("countryCode") @ApiParam("agent country code") String countryCode) {
        AgentInfo agent = agentService.getAgent(uid, countryCode);
        return new ResponseEntity<>(agent, agent != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

	@PutMapping(
			path = "/agent")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Save (insert/update) agent info.")
	public ResponseEntity<AgentInfo> putAgent(@Valid @RequestBody AgentInfo agentInfo) {
		return new ResponseEntity<>(agentService.save(agentInfo), HttpStatus.OK);
    }

	@DeleteMapping(
			path = "/agent/{id}")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Delete Agent by id.")
	public ResponseEntity<String> removeAgent(@PathVariable("id") @ApiParam("id") String id) {
		String deleteResult = agentService.remove(id);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
    }
	
	@DeleteMapping(
			path = "/agent/{uid}/{countryCode}")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Delete Agent by uid and countryCode.")
	public ResponseEntity<String> removeAgent(@PathVariable("uid") @ApiParam("uid") String uid, @PathVariable("countryCode") @ApiParam("countryCode") String countryCode) {
		String deleteResult = agentService.remove(uid, countryCode);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
    }
}
