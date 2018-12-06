package com.cwt.bpg.cbt.exchange.order;

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

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.RoomType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Exchange Order - Hotel Room Types")
public class HotelRoomTypesController {

	@Autowired
	private ExchangeOrderService eoService;

	@GetMapping(path = "/exchange-order/room-types", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns all hotel room types.")
	public ResponseEntity<List<RoomType>> getAll() {

		return new ResponseEntity<>((List<RoomType>)
				eoService.getAll(), HttpStatus.OK);
	}

	@Internal
	@PutMapping(path = "/exchange-order/room-types")
	@ApiOperation(value = "Saves (inserts/updates) hotel room type.")
	@ResponseBody
	public ResponseEntity<RoomType> putRoomType(@Valid @RequestBody RoomType roomType) {
		return new ResponseEntity<>(eoService.save(roomType), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/exchange-order/room-types/{code}")
	@ResponseBody
	@ApiOperation(value = "Deletes hotel room type given room type code.")
	public ResponseEntity<String> removeRoomType(@PathVariable String code) {
		String deleteResult = eoService.delete(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);

	}
	
	private HttpStatus checkDeleteResult(String deleteResult) {
		return deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
	}
}
