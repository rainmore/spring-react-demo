package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Role;
import au.com.rainmore.centus.services.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

}
