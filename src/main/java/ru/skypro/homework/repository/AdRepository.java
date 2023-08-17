package ru.skypro.homework.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository <Ad, Integer> {

    Optional<Ad> findAdById(Integer id);
    List<Ad> findAllByUser(User user);
}
