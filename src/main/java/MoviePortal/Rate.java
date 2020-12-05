/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package MoviePortal;

import ObjModelAnalysis.annotations.Column;
import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.Id;

import java.util.Date;

@Entity
public class Rate implements ORMManagement.Entity<Long> {
    @Id
    private long id;
    @Column
    private byte value;
    @Column
    private Date dateOfChange;

    public Rate(byte value, Date dateOfChange) {
        this.value = value;
        this.dateOfChange = dateOfChange;
    }

    public Rate() {
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public Date getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(Date dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "value=" + value +
                ", dateOfChange=" + dateOfChange +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
