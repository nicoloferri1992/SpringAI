package com.AI.adoptions;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("dog")
public record Dog(@Id Integer id, String name, String owner, String description) {
}
