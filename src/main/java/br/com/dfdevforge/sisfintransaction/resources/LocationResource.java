package br.com.dfdevforge.sisfintransaction.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.repositories.LocationRepository;

@RestController
@RequestMapping(value = "/location")
public class LocationResource {
	@Autowired
	private LocationRepository locationRepository;

	@GetMapping(value = "/{identity}")
	public ResponseEntity<LocationEntity> findByIdentity(@PathVariable Long identity) {
		LocationEntity locationEntity = this.locationRepository.findByIdentity(identity);

		return ResponseEntity.ok(locationEntity);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<LocationEntity> findByName(@RequestParam String name) {
		LocationEntity locationEntity = this.locationRepository.findByName(name);

		return ResponseEntity.ok(locationEntity);
	}
}