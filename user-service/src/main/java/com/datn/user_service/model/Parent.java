package com.datn.user_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "parents")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Parent extends User {
    @Column(name = "is_notification_on")
    private Boolean isNotificationOn;

    @OneToMany(mappedBy = "parent")
    private List<ParentStudent> students;
}