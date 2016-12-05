package com.hospital.management.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hospital.management.domain.Organization;
import com.hospital.management.service.OrganizationService;
import com.hospital.management.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/api")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);
        
    @Inject
    private OrganizationService organizationService;

    /**
     * POST  /organizations : Create a new organization.
     *
     * @param organization the organization to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organization, or with status 400 (Bad Request) if the organization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organizations")
    @Timed
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organization);
        if (organization.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("organization", "idexists", "A new organization cannot already have an ID")).body(null);
        }
        Organization result = organizationService.save(organization);
        return ResponseEntity.created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("organization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizations : Updates an existing organization.
     *
     * @param organization the organization to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organization,
     * or with status 400 (Bad Request) if the organization is not valid,
     * or with status 500 (Internal Server Error) if the organization couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organizations")
    @Timed
    public ResponseEntity<Organization> updateOrganization(@RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to update Organization : {}", organization);
        if (organization.getId() == null) {
            return createOrganization(organization);
        }
        Organization result = organizationService.save(organization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("organization", organization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizations : get all the organizations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organizations in body
     */
    @GetMapping("/organizations")
    @Timed
    public List<Organization> getAllOrganizations() {
        log.debug("REST request to get all Organizations");
        return organizationService.findAll();
    }

    /**
     * GET  /organizations/:id : get the "id" organization.
     *
     * @param id the id of the organization to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organization, or with status 404 (Not Found)
     */
    @GetMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<Organization> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        Organization organization = organizationService.findOne(id);
        return Optional.ofNullable(organization)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /organizations/:id : delete the "id" organization.
     *
     * @param id the id of the organization to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organization", id.toString())).build();
    }

}
