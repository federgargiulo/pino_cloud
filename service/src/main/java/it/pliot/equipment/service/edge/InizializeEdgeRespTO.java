package it.pliot.equipment.service.edge;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.TenantTO;

public class InizializeEdgeRespTO {

    private TenantTO tenant;

    private EdgeTO edge;

    public TenantTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantTO tenant) {
        this.tenant = tenant;
    }

    public EdgeTO getEdge() {
        return edge;
    }

    public void setEdge(EdgeTO edge) {
        this.edge = edge;
    }

    public InizializeEdgeRespTO( EdgeTO edge , TenantTO tenant) {
        this.tenant = tenant;
        this.edge = edge;
    }

    public InizializeEdgeRespTO(){}
}
