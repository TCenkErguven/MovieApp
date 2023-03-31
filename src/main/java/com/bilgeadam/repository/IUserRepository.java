package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmailAndPassword(String email, String password);
    //Kullanıcıları ismine göre sıralayan bir metot yazınız.

    //@Query("select u from User u Order BY LOWER(u.name)")
    //List<User> findAllByNameOrderBy();
    //Kullanıcının girdiği bir isimle veritabanındaki bir isim var mıdır?
    List<User> findAllByOrderByName();
    List<User> findByName(String name);
    //Kullanıcının isim sorgulaması için girdiği harf veya kelimeye göre veritabanında sorgu yapan bir metot yazınız.
    boolean existsAllByNameIgnoreCase(String name); //eklenecek
    List<User> findByNameContainingIgnoreCase (String name);
    //Kullanıcının girdiği email'e göre veritabanında sorgu yapan bir metot yazınız.
    Optional<User> findAllByEmailIgnoreCase (String email);
    Optional<User> findByEmailEqualsIgnoreCase(String email);
    //Passwordunun uzunluğu belirlediğimiz sayıdan buyuk olanlar (2 tür Query yazılacak)
    @Query("select u from User u where LENGTH( u.password) > ?1")
    List<User> findAllPasswordGreaterThanJpql(Integer value);
    @Query(value = "select * from User u where LENGTH(u.password = :parampassword) > ?1",nativeQuery = true)
    List<User> findAllPasswordGreaterThanNative(
            @Param("parampassword") Integer value);
    List<User> findAllByEmailEndsWithIgnoreCase(String value);
}
