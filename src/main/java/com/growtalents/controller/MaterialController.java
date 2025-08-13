    package com.growtalents.controller;

    import com.growtalents.dto.request.Material.MaterialUpsertRequestDTO;
    import com.growtalents.dto.response.GlobalResponse;
    import com.growtalents.dto.response.Material.MaterialResponseDTO;
    import com.growtalents.enums.MaterialType;
    import com.growtalents.service.Interfaces.MaterialService;
    import io.swagger.v3.oas.annotations.Operation;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/materials")

    public class MaterialController {

        private final MaterialService materialService;

        @PostMapping("/upsert")
        @Operation(summary = "Upsert material component")
        public ResponseEntity<GlobalResponse<MaterialResponseDTO>> upsert(@Valid @RequestBody MaterialUpsertRequestDTO dto) {
            var data = materialService.upsert(dto);
            return ResponseEntity.ok(GlobalResponse.success("Saved", data));
        }

        @GetMapping("/courses/{courseId}")
        @Operation(summary = "Lấy material cho course")
        public ResponseEntity<GlobalResponse<MaterialResponseDTO>> get(
                @PathVariable String courseId,
                @RequestParam MaterialType type
        ) {
            var data = materialService.getByCourseAndType(courseId, type);
            return ResponseEntity.ok(GlobalResponse.success(data));
        }

        @DeleteMapping("/courses/{courseId}")
        @Operation(summary = "Delete material component by type")
        public ResponseEntity<GlobalResponse<Void>> delete(
                @PathVariable String courseId,
                @RequestParam MaterialType type
        ) {
            materialService.deleteByCourseAndType(courseId, type);
            return ResponseEntity.ok(GlobalResponse.success("Deleted", null));
        }
    }
