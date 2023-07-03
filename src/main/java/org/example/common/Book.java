package org.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-01 14:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Book {

  private Integer id;

  private String bookName;

  private double price;

  private String author;
}
