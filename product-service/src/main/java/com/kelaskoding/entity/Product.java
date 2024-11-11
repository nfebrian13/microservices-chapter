package com.kelaskoding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data // Akan menghasilkan getter, setter, toString, equals, dan hashCode
@NoArgsConstructor // Menghasilkan constructor tanpa argumen
@AllArgsConstructor // Menghasilkan constructor dengan semua argumen
@Builder // Menyediakan pattern Builder untuk pembuatan objek
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment untuk id
	private Long id;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private Double price;

	@Column(length = 200, nullable = true, unique = false)
	private String description;
}
