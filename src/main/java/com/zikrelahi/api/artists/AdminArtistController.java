package com.zikrelahi.api.artists;

import com.zikrelahi.api.common.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/admin/artists")
@RequiredArgsConstructor
public class AdminArtistController {

    private final AdminArtistService artistService;

    @PostMapping
    public ResponseEntity<ApiResponse<ArtistDtos.AdminArtistDto>> create(
            @Valid @RequestBody ArtistDtos.CreateArtistRequest request) {
        ArtistDtos.AdminArtistDto created = artistService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ArtistDtos.PageSlice<ArtistDtos.AdminArtistDto>>> list(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String query) {
        return ResponseEntity.ok(ApiResponse.ok(artistService.list(page, size, status, role, query)));
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<ArtistDtos.RoleDto>>> roles() {
        return ResponseEntity.ok(ApiResponse.ok(artistService.roles()));
    }

    @GetMapping("/options")
    public ResponseEntity<ApiResponse<List<ArtistDtos.ArtistOptionDto>>> options(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String query) {
        return ResponseEntity.ok(ApiResponse.ok(artistService.options(role, query)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArtistDtos.AdminArtistDto>> get(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(artistService.get(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ArtistDtos.AdminArtistDto>> update(
            @PathVariable String id,
            @Valid @RequestBody ArtistDtos.UpdateArtistRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(artistService.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ArtistDtos.AdminArtistDto>> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody ArtistDtos.StatusRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(artistService.updateStatus(id, request.status())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}