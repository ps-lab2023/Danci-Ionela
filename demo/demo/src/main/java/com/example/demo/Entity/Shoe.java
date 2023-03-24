package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Shoe {
    @Id
    private Long shoe_id;
    @Column(nullable= false,length = 30)
    private String shoe_name;
    @Column(nullable= false,length = 30)
    private String shoe_type;
    @Column(nullable= true,length = 200)
    private String shoe_desc;
    @Column(nullable= false,length = 30)
    private String shoe_brand;

}
