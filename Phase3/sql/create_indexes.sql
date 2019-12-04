-- HOTEL --
CREATE INDEX Hotel_hotelID -- Numeric
ON Hotel USING BTREE(hotelID);

CREATE INDEX Hotel_address  --text 
ON Hotel USING BTREE (address);

CREATE INDEX Hotel_manager -- Numeric
ON Hotel USING BTREE (manager); 

-- STAFF --
CREATE INDEX Staff_SSN -- Numeric
ON Staff USING BTREE (SSN);

CREATE INDEX Staff_fname --fname
ON Staff USING BTREE (fname);

CREATE INDEX Staff_lname -- lname
ON Staff USING BTREE (lname);

CREATE INDEX Staff_address -- Text
ON Staff USING BTREE (address);

CREATE INDEX Staff_role -- StaffRole
ON Staff USING BTREE (role);

CREATE INDEX Staff_employerID
ON Staff USING BTREE (employerID);

-- ROOM --
CREATE INDEX Room_hotelID -- Numeric
ON Room USING BTREE (hotelID);

CREATE INDEX Room_roomNo -- Numeric
ON Room USING BTREE (roomNo);

CREATE INDEX Room_roomType -- Char(10)
ON Room USING BTREE (roomType);

-- CUSTOMER --
CREATE INDEX Customer_customerID -- Numeric
ON Customer USING BTREE (customerID);

CREATE INDEX Customer_fname --fname
ON Customer USING BTREE (fname);

CREATE INDEX Customer_lname -- lname
ON Customer USING BTREE (lname);

CREATE INDEX Customer_Address -- Text
ON Customer USING BTREE (Address);

CREATE INDEX Customer_phNO-- Numeric
ON Customer USING BTREE (phNo);

CREATE INDEX Customer_DOB-- Date
ON Customer USING BTREE (DOB);

CREATE INDEX Customer_gender -- GenderType
ON Customer USING BTREE (gender);

-- MAINTENANCE COMPANY --
CREATE INDEX MaintenanceCompany_cmpID -- Numeric
ON MaintenanceCompany USING BTREE (cmpID);

CREATE INDEX MaintenanceCompany_name -- Char
ON MaintenanceCompany USING BTREE (name);

CREATE INDEX MaintenanceCompany_address -- Text
ON MaintenanceCompany USING BTREE (address);

CREATE INDEX MaintenanceCompan_isCertified -- Boolean
ON MaintenanceCompany USING BTREE (isCertified);

-- BOOKING --
CREATE INDEX Booking_bID -- Numeric
ON Booking USING BTREE (bID);

CREATE INDEX Booking_customer-- Numeric
ON Booking USING BTREE (customer);

CREATE INDEX Booking_hotelID -- Numeric
ON Booking USING BTREE (hotelID);

CREATE INDEX Booking_roomNo -- Numeric
ON Booking USING BTREE (roomNo);

CREATE INDEX Booking_bookingDate -- Date
ON Booking USING BTREE (bookingDate);

CREATE INDEX Booking_noOfPeople -- Numeric
ON Booking USING BTREE (noOfPeople);

CREATE INDEX Booking_price -- Numeric
ON Booking USING BTREE (price);

-- REPAIR --
CREATE INDEX Repair_rID -- Numeric
ON Repair USING BTREE (rID);

CREATE INDEX Repair_hotelID -- Numeric
ON Repair USING BTREE (hotelID);

CREATE INDEX Repair_roomNo -- Numeric
ON Repair USING BTREE (roomNo);

CREATE INDEX Repair_mCompany -- Numeric
ON Repair USING BTREE (mCompany);

CREATE INDEX Repair_repairDate -- Date
ON Repair USING BTREE (repairDate);

CREATE INDEX Repair_description -- Text
ON Repair USING BTREE (description);

CREATE INDEX Repair_repairType -- Char
ON Repair USING BTREE (repairType);

-- REQUEST --
CREATE INDEX Request_reqID -- Numeric
ON Request USING BTREE (reqID);

CREATE INDEX Request_managerID -- Numeric
ON Request USING BTREE (managerID);

CREATE INDEX Request_repairID -- Numeric
ON Request USING BTREE (repairID);

CREATE INDEX Request_requestDate -- Date
ON Request USING BTREE (requestDate);

CREATE INDEX Request_description -- Text
ON Request USING BTREE (description);

-- ASSIGNED --
CREATE INDEX Assigned_asgID -- Numeric
ON Assigned USING BTREE (asgID);

CREATE INDEX Assigned_staffID -- Numeric
ON Assigned USING BTREE (staffID);

CREATE INDEX Assigned_hotelID -- Numeric
ON Assigned USING BTREE (hotelID);

CREATE INDEX Assigned_roomNo -- Numeric
ON Assigned USING BTREE (roomNo);
