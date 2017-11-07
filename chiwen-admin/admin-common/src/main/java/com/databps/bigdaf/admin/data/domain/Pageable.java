package com.databps.bigdaf.admin.data.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * @author merlin
 * @create 2017-09-08 下午5:44
 */
public interface Pageable {

  /**
   * Returns the page to be returned.
   *
   * @return the page to be returned.
   */
  int getPageNumber();

  /**
   * Returns the number of items to be returned.
   *
   * @return the number of items of that page
   */
  int getPageSize();

  /**
   * Returns the offset to be taken according to the underlying page and page size.
   *
   * @return the offset to be taken
   */
  int getOffset();

  /**
   * Returns the sorting parameters.
   *
   * @return
   */
  Sort getSort();

  /**
   * Returns the {@link org.springframework.data.domain.Pageable} requesting the next {@link Page}.
   *
   * @return
   */
  Pageable next();

  /**
   * Returns the previous {@link org.springframework.data.domain.Pageable} or the first {@link org.springframework.data.domain.Pageable} if the current one already is the first one.
   *
   * @return
   */
  Pageable previousOrFirst();

  /**
   * Returns the {@link org.springframework.data.domain.Pageable} requesting the first page.
   *
   * @return
   */
  Pageable first();

  /**
   * Returns whether there's a previous {@link Pageable} we can access from the current one. Will return
   * {@literal false} in case the current {@link Pageable} already refers to the first page.
   *
   * @return
   */
  boolean hasPrevious();
}
