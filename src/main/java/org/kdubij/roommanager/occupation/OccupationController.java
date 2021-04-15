package org.kdubij.roommanager.occupation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class OccupationController {
    private final RoomOccupationService service;

    @PostMapping("/occupations")
    public ResponseEntity<OccupationResult> occupation(@RequestBody OccupationRequest occupationRequest) {
        log.info("[OCCUPATION] START {request={}}", occupationRequest);
        var occupationResult = service.occupy(occupationRequest.getFreePremiumRooms(), occupationRequest.getFreeEconomyRooms());
        log.info("[OCCUPATION] END");
        return ResponseEntity.ok(occupationResult);
    }
}
