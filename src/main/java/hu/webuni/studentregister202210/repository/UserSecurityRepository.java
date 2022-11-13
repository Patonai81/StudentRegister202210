package hu.webuni.studentregister202210.repository;

import hu.webuni.studentregister202210.model.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {

    @Query("select u from UserSecurity  u where u.userName= :userName")
    UserSecurity findByUserName(String userName);
}
