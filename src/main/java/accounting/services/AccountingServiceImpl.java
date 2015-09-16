/*
 Copyright (c) 2012 Red Hat, Inc.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are
 met:

   1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 THIS SOFTWARE IS PROVIDED BY RED HAT, INC. ``AS IS'' AND ANY EXPRESS
 OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL RED HAT, INC. BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */

package accounting.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import accounting.db.Invoice;

@Stateless
public class AccountingServiceImpl implements AccountingService,
		Serializable {

	private static final long serialVersionUID = 1L;
	private static long INVOICE_NUM = 0L;

	private static Map<Long, Invoice> invoices = new HashMap<Long, Invoice>();

	public long create(Invoice invoice) {
		invoice.setInvoiceNumber(++INVOICE_NUM);
		invoices.put(invoice.getInvoiceNumber(), invoice);
		return invoice.getInvoiceNumber();
	}

	@Produces
	@Named("invoiceReport")
	public List<Invoice> getInvoices() {
		List<Invoice> list = new ArrayList<Invoice>();
		for (Map.Entry<Long, Invoice> entry : invoices.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

}
