package com.zeldev.zel_e_comm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "confirmations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ConfirmationEntity extends BaseEntity {
    @Column(name = "conf_key")
    private String confKey;
    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("user_id")
    private UserEntity user;

    public ConfirmationEntity(UserEntity user, String key){
        this.user = user;
        this.confKey = key;
    }
}
