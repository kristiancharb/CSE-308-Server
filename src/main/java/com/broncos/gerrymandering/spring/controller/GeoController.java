package com.broncos.gerrymandering.spring.controller;

import com.broncos.gerrymandering.model.District;
import com.broncos.gerrymandering.model.Precinct;
import com.broncos.gerrymandering.model.State;
import com.broncos.gerrymandering.model.StateCode;
import com.broncos.gerrymandering.util.BoundaryWrapper;
import com.broncos.gerrymandering.util.StateManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by mpokr on 11/25/2018.
 */
@CrossOrigin(origins = "http://localhost:9000")
@RestController
public class GeoController {

    @RequestMapping(value = "/state-geo",
            method = RequestMethod.GET,
            params = {"stateCode"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStateGeo(StateCode stateCode) {
        StateManager sm = StateManager.getInstance();
        State state = sm.getState(stateCode);
        return state.getBoundary();
    }

    @RequestMapping(value = "/district-geo",
            method = RequestMethod.GET,
            params = {"stateCode"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, BoundaryWrapper> getDistrictGeo(StateCode stateCode) {
        StateManager sm = StateManager.getInstance();
        State state = sm.getState(stateCode);
        Map<Integer, BoundaryWrapper> boundaryByDistrictId = new HashMap<>();
        Set<District> districts = state.getDistricts();
        for (District district : districts) {
            boundaryByDistrictId.put(district.getDistrictId(), new BoundaryWrapper(district.getBoundary()));
        }
        return boundaryByDistrictId;
    }

    @RequestMapping(value = "/precinct-geo",
            method = RequestMethod.GET,
            params = {"districtId", "stateCode"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, BoundaryWrapper> getPrecinctGeo(Integer districtId, StateCode stateCode) {
        StateManager sm = StateManager.getInstance();
        District district = sm.getDistrict(districtId, stateCode);
        if (district == null) {
            return null;
        }
        Set<Precinct> precincts = district.getPrecincts();
        Map<Integer, BoundaryWrapper> boundaryByPrecinctId = new HashMap<>();
        for (Precinct precinct : precincts) {
            boundaryByPrecinctId.put(precinct.getPrecinctId(), new BoundaryWrapper(precinct.getBoundary()));
        }
        return boundaryByPrecinctId;
    }

}
