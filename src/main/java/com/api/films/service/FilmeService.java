package com.api.films.service;

import com.api.films.repository.AssistidoRepository;
import com.api.films.repository.FilmeRepository;
import com.api.films.repository.ReviewRepository;
import com.api.films.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final AssistidoRepository assistidoRepository;
    private final WishlistRepository wishlistRepository;
    private final ReviewRepository reviewRepository;

    public FilmeService(FilmeRepository filmeRepository, AssistidoRepository assistidoRepository, WishlistRepository wishlistRepository, ReviewRepository reviewRepository) {
        this.filmeRepository = filmeRepository;
        this.assistidoRepository = assistidoRepository;
        this.wishlistRepository = wishlistRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public boolean deletarFilme(Integer id) {
        if (!filmeRepository.existsById(id)) {
            return false;
        }

        assistidoRepository.deleteByFilmeId(id);
        wishlistRepository.deleteByFilmeId(id);
        reviewRepository.deleteByFilmeId(id);
        filmeRepository.deleteById(id);

        return true;
    }


}
