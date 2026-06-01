package com.example.demo.controller;

import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.repository.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes(@AuthenticationPrincipal User user) {
        if (user.getRole().name().equals("ADMIN")) {
            return ResponseEntity.ok(noteRepository.findAll());
        }
        return ResponseEntity.ok(noteRepository.findByUser(user));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note, @AuthenticationPrincipal User user) {
        note.setUser(user);
        return ResponseEntity.ok(noteRepository.save(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note noteDetails, @AuthenticationPrincipal User user) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));

        if (!user.getRole().name().equals("ADMIN") && !note.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        return ResponseEntity.ok(noteRepository.save(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));

        if (!user.getRole().name().equals("ADMIN") && !note.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        noteRepository.delete(note);
        return ResponseEntity.noContent().build();
    }
}
