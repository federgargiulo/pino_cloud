package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.model.BaseEntity;
import it.pliot.equipment.model.DashboardConfiguration;
import it.pliot.equipment.model.Measure;
import it.pliot.equipment.repository.DashboardConfigurationRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.security.JwtUser;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.DashboardConfigurationService;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.DashboardConfigurationUtils;
import it.pliot.equipment.utils.DashboardConfigurationSpecifications;
import it.pliot.equipment.utils.MeasureSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Transactional
public class DashboardConfigurationServiceImpl
        extends BaseServiceImpl<DashboardConfigurationTO, DashboardConfiguration , String>
            implements DashboardConfigurationService {


    @Autowired
    private DashboardConfigurationRepository repo;

    @Override
    public PliotJpaRepository<DashboardConfiguration, String> getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return DashboardConfigurationUtils.instance();
    }


    public List<DashboardConfigurationTO> findUserDashboards(){
        JwtUser user = UserContext.currentUser();
        DashboardConfiguration probe = new DashboardConfiguration();
        if ( user != null )
            probe.setUserIdpId( user.getIdpId() );
        Example<DashboardConfiguration> example = Example.of(probe);
        List<DashboardConfiguration> configurations= getRepo().findAll(example);
        return getConverter().converListData2IO( configurations );
    }

    public PagedResultTO<DashboardConfigurationTO> findAccessibleDashboardsOfCurrentUser(String page , int pageSize ){
        JwtUser user = UserContext.currentUser();
        DashboardConfiguration probe = new DashboardConfiguration();
        if ( user != null )
            probe.setUserIdpId( user.getIdpId() );
        Pageable nextPage  = PageRequest.of( Integer.valueOf( page ) ,  pageSize );
        Specification<DashboardConfiguration> spec = Specification
                .where( DashboardConfigurationSpecifications.isOwnedByUser( user.getIdpId() ) )  // Prezzo > valore (AND)
                .or( DashboardConfigurationSpecifications.isSharedInAtenant( user.getTenantId() ) );
        try {
            return findPaged(spec, nextPage);
        }catch ( Exception e ){
            e.getStackTrace();
            throw  new RuntimeException( " errore in lettura ");
        }
    }



    public DashboardConfigurationTO update(DashboardConfigurationTO io ){
        Optional<DashboardConfiguration> op = getRepo().findById( io.getId() );
        if (op.isEmpty())
            throw  new RuntimeException( " not found entity DashboardConfiguration " + io.getId() );
        DashboardConfiguration entity = op.get();
        entity.setTitle( io.getTitle() );
        entity.setDescr( io.getDescr() );
        entity.setConfiguration( io.getConfiguration() );
        entity.setShared( io.getShared() );
        return (DashboardConfigurationTO) getConverter().data2io( ( DashboardConfiguration ) getRepo().save( entity ) );
    }
}
