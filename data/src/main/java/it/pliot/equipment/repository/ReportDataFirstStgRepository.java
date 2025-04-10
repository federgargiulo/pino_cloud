package it.pliot.equipment.repository;

import it.pliot.equipment.model.ReportDataFirstStg;
import it.pliot.equipment.model.UserGrp;
import jakarta.transaction.Transactional;

@Transactional
public interface ReportDataFirstStgRepository  extends PliotJpaRepository<ReportDataFirstStg, String>{
}
