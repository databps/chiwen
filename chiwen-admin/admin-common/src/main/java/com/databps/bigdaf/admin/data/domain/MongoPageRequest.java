package com.databps.bigdaf.admin.data.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author merlin
 * @create 2017-09-08 下午5:16
 */
public  class MongoPageRequest extends AbstractMongoPageRequest {

  private static final long serialVersionUID = -4541509938956089562L;

  private final Sort sort;

  /**
   * Creates a new {@link MongoPageRequest}. Pages are zero indexed, thus providing 0 for {@code page} will return the first
   * page.
   *
   * @param page zero-based page index.
   * @param size the size of the page to be returned.
   */
  public MongoPageRequest(int page, int size) {
    this(page, size, null);
  }

  /**
   * Creates a new {@link MongoPageRequest} with sort parameters applied.
   *
   * @param page zero-based page index.
   * @param size the size of the page to be returned.
   * @param direction the direction of the {@link Sort} to be specified, can be {@literal null}.
   * @param properties the properties to sort by, must not be {@literal null} or empty.
   */
  public MongoPageRequest(int page, int size, Direction direction, String... properties) {
    this(page, size, new Sort(direction, properties));
  }

  /**
   * Creates a new {@link MongoPageRequest} with sort parameters applied.
   *
   * @param page zero-based page index.
   * @param size the size of the page to be returned.
   * @param sort can be {@literal null}.
   */
  public MongoPageRequest(int page, int size, Sort sort) {
    super(page, size);
    this.sort = sort;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.data.domain.Pageable#getSort()
   */
  public Sort getSort() {
    return sort;
  }

  /* 
   * (non-Javadoc)
   * @see org.springframework.data.domain.Pageable#next()
   */
  public Pageable next() {
    return new MongoPageRequest(getPageNumber() + 1, getPageSize(), getSort());
  }

  /* 
   * (non-Javadoc)
   * @see org.springframework.data.domain.AbstractPageRequest#previous()
   */
  public MongoPageRequest previous() {
    return getPageNumber() == 0 ? this : new MongoPageRequest(getPageNumber() - 1, getPageSize(), getSort());
  }

  /* 
   * (non-Javadoc)
   * @see org.springframework.data.domain.Pageable#first()
   */
  public Pageable first() {
    return new MongoPageRequest(0, getPageSize(), getSort());
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof MongoPageRequest)) {
      return false;
    }

    MongoPageRequest that = (MongoPageRequest) obj;

    boolean sortEqual = this.sort == null ? that.sort == null : this.sort.equals(that.sort);

    return super.equals(that) && sortEqual;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return 31 * super.hashCode() + (null == sort ? 0 : sort.hashCode());
  }

  /* 
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(),
        sort == null ? null : sort.toString());
  }
}
