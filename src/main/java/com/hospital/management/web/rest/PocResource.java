package com.hospital.management.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hospital.management.domain.Poc;
import com.hospital.management.service.PocService;
import com.hospital.management.web.rest.util.HeaderUtil;
import com.hospital.management.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Poc.
 */
@RestController
@RequestMapping("/api")
public class PocResource {

    private final Logger log = LoggerFactory.getLogger(PocResource.class);
        
    @Inject
    private PocService pocService;

    /**
     * POST  /pocs : Create a new poc.
     *
     * @param poc the poc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poc, or with status 400 (Bad Request) if the poc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pocs")
    @Timed
    public ResponseEntity<Poc> createPoc(@RequestBody Poc poc) throws URISyntaxException {
        log.debug("REST request to save Poc : {}", poc);
        if (poc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("poc", "idexists", "A new poc cannot already have an ID")).body(null);
        }
        Poc result = pocService.save(poc);
        return ResponseEntity.created(new URI("/api/pocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("poc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pocs : Updates an existing poc.
     *
     * @param poc the poc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poc,
     * or with status 400 (Bad Request) if the poc is not valid,
     * or with status 500 (Internal Server Error) if the poc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pocs")
    @Timed
    public ResponseEntity<Poc> updatePoc(@RequestBody Poc poc) throws URISyntaxException {
        log.debug("REST request to update Poc : {}", poc);
        if (poc.getId() == null) {
            return createPoc(poc);
        }
        Poc result = pocService.save(poc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("poc", poc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pocs : get all the pocs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pocs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pocs")
    @Timed
    public ResponseEntity<List<Poc>> getAllPocs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pocs");
        Page<Poc> page = pocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pocs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pocs/:id : get the "id" poc.
     *
     * @param id the id of the poc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poc, or with status 404 (Not Found)
     */
    @GetMapping("/pocs/{id}")
    @Timed
    public ResponseEntity<Poc> getPoc(@PathVariable Long id) {
        log.debug("REST request to get Poc : {}", id);
        Poc poc = pocService.findOne(id);
        return Optional.ofNullable(poc)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pocs/:id : delete the "id" poc.
     *
     * @param id the id of the poc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pocs/{id}")
    @Timed
    public ResponseEntity<Void> deletePoc(@PathVariable Long id) {
        log.debug("REST request to delete Poc : {}", id);
        pocService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("poc", id.toString())).build();
    }

}
