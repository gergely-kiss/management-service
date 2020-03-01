package uk.kissgergely.managementservice.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.kissgergely.managementservice.api.resources.ControllerConstants;

@RestController
@RequestMapping(ControllerConstants.API_ROOT + ControllerConstants.TAG_PATH)
public class TagController {
}
