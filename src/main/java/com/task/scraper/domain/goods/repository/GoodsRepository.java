package com.task.scraper.domain.goods.repository;

import com.task.scraper.domain.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, String> {
}
