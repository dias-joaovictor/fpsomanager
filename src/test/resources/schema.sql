create table if not exists FPSO_VESSEL (
    ID Identity not null primary key,
    registryCode varchar(20) not null,
    UNIQUE KEY fpso_vessel_regcode (registryCode)  
);

create table if not exists FPSO_EQUIPMENT (
    ID Identity not null primary key,
	name varchar(50) not null,
	code varchar(20) not null,
	vesselId int not null,
	equipmentStatus tinyint not null,
	location varchar(30) not null,
    FOREIGN KEY (vesselID) references FPSO_VESSEL(ID),
    UNIQUE KEY fpso_equipment_code (code)  
);
