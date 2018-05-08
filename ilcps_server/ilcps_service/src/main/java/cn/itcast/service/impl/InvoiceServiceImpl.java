package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.InvoiceDao;
import cn.itcast.domain.Invoice;
import cn.itcast.service.InvoiceService;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

	@Resource
	private InvoiceDao invoiceDao;
	
	@Override
	public List<Invoice> findAll() {
		return invoiceDao.findAll();
	}

	@Override
	public Invoice findOne(String id) {
		return invoiceDao.findOne(id);
	}

	@Override
	public List<Invoice> findAll(Specification<Invoice> spec) {
		return invoiceDao.findAll(spec);
	}

	@Override
	public Page<Invoice> findAll(Pageable pageable) {
		return invoiceDao.findAll(pageable);
	}

	@Override
	public Page<Invoice> findAll(Specification<Invoice> spec, Pageable pageable) {
		return invoiceDao.findAll(spec, pageable);
	}

	@Override
	public void saveOrUpdate(Invoice t) {
		invoiceDao.save(t);
		
	}

	@Override
	public void delete(String id) {
		invoiceDao.delete(id);
	}

}
