package com.devaleriola.speos_assessment.controllers;

import com.devaleriola.speos_assessment.entities.partner.Partner;
import com.devaleriola.speos_assessment.entities.partner.PartnerDto;
import com.devaleriola.speos_assessment.services.partner.PartnerService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/partners", produces = "application/json")
public class PartnerController {

    @Autowired
    private PartnerService service;

    @GetMapping
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Partner.class)))
    })
    public ResponseEntity<List<PartnerDto>> getPartners(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return new ResponseEntity<>(service.getAllEntities(from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Partner.class))
    })
    public ResponseEntity<PartnerDto> getPartner(
            @PathVariable(name = "id", required = true) long id
    ) {
        return new ResponseEntity<>(service.getEntityById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiResponse(responseCode = "201", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Partner.class))
    })
    public ResponseEntity<PartnerDto> createPartner(
            @RequestBody Partner partner
    ) {
        return new ResponseEntity<>(service.createEntity(partner), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Partner.class))
    })
    public ResponseEntity<PartnerDto> updatePartner(
            @PathVariable(name = "id", required = true) long id,
            @RequestBody Partner partner
    ) {
        return new ResponseEntity<>(service.updateEntity(id, partner), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json")
    })
    public void deletePartner(
            @PathVariable(name = "id", required = true) long id
    ) {
    }
}
