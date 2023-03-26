package com.hossein.PermissionTree.dao.repository.impl.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hossein.PermissionTree.controller.viewModel.ProductViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;
import com.hossein.PermissionTree.dto.product.ProductDto;

@Repository
public class ProductCustomRepositoryImpl extends GenericRepository implements ProductCustomRepository {

	@Override
	public List<ProductViewModel> getAll(ProductDto data) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select"
				+ " e.id as productId, e.name as productName,"
				+ " e.price as productPrice, e.count as productCount,"
				+ " e.dateAdded as productDateAdded, e.dateModified as productDateModified"
				+ " from Product e where 1=1");
		
		if (StringUtils.hasText(data.getName())) {
			hql.append(" and e.name like :productName");
			params.put("productName", "%"+data.getName()+"%");
		}
		
		if (data.getPrice() != null && data.getPrice() >= -1) {
			hql.append(" and e.price = :productPrice");
			params.put("productPrice", data.getPrice());
		}
		
		if (data.getCount() != null && data.getCount() >= -1) {
			hql.append(" and e.count = :productCount");
			params.put("productCount", data.getCount());
		}
		
		if (StringUtils.hasText(data.getDateAdded())) {
			hql.append(" and e.dateAdded like :dateAdded");
			params.put("dateAdded", "%"+data.getDateAdded()+"%");
		}
		
		if (StringUtils.hasText(data.getDateModified())) {
			hql.append(" and e.dateModified like :dateModified");
			params.put("dateModified", "%"+data.getDateModified()+"%");
		}
		
		return super.getAll(hql.toString(), params, ProductViewModel.class);
	}

}
