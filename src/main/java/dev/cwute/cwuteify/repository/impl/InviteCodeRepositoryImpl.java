package dev.cwute.cwuteify.repository.impl;

import dev.cwute.cwuteify.model.InviteCode;
import dev.cwute.cwuteify.repository.InviteCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public abstract class InviteCodeRepositoryImpl implements InviteCodeRepository {

  @Autowired EntityManager entityManager;

  @Override
  public Optional<InviteCode> findByCode(String code) {
    var criteriaBuilder = entityManager.getCriteriaBuilder();
    var criteriaQuery = criteriaBuilder.createQuery(InviteCode.class);
    Root<InviteCode> root = criteriaQuery.from(InviteCode.class);
    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("code"), code));
    return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
  }
}
