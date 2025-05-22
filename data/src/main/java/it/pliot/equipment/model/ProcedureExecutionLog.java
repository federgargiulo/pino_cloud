package it.pliot.equipment.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity( name = "procedure_execution_log" )
@SequenceGenerator(
        name = "procedure_execution_log_seq",
        sequenceName = "procedure_execution_log_seq",
        allocationSize = 1  // Imposta il passo a 1
)
public class ProcedureExecutionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "procedure_execution_log_seq" )
    private Long id;

    @Column( name = "procedure_name" , nullable = false )
    private String procedureName;

    @Column( name = "start_time" , columnDefinition = "TIMESTAMP DEFAULT NOW()" )
    private Date startTime;
    @Column( name = "end_time"   )
    private Date endTime;

    @Column( name = "status"   )
    private String status;

    @Column( name = "message" , columnDefinition = "TEXT" )
    private String  message;




}
