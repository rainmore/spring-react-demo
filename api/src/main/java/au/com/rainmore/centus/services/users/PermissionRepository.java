package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Permission;
import au.com.rainmore.centus.services.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long> {

}
