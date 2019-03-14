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
import com.cwt.bpg.cbt.documentation.annotation.Internal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Agent")
public class AgentController {

	@Autowired
	private AgentService agentService;

	@GetMapping(value = "/agent", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns list of all agents.")
	public ResponseEntity<List<AgentInfo>> getAgents() {
		return new ResponseEntity<>(agentService.getAgents(), HttpStatus.OK);
	}

	@GetMapping(value = "/agent/{uid}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns Agent given an uid.")
	public ResponseEntity<AgentInfo> getAgent(@PathVariable @ApiParam("agent uid") String uid) {
        AgentInfo agent = agentService.getAgent(uid);
        return new ResponseEntity<>(agent, agent != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

	@Internal
	@PutMapping(path = "/agent")
	@ApiOperation(value = "[Maintenance] Saves (inserts/updates) Agents.")
	@ResponseBody
	public ResponseEntity<AgentInfo> putAgent(@Valid @RequestBody AgentInfo agentInfo) {
		return new ResponseEntity<>(agentService.save(agentInfo), HttpStatus.OK);
    }

	@Internal
	@DeleteMapping(path = "/agent/{uid}")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Deletes Agent given an uid.")
	public ResponseEntity<String> removeAgent(@PathVariable @ApiParam("agent uid") String uid) {
		String deleteResult = agentService.delete(uid);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
    }

}
