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

package accounting.web;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.cdi.push.Push;

import accounting.db.Invoice;
import accounting.services.AccountingService;

@Model
public class OrderEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AccountingService service;

	@Named
	@Produces
	private Invoice invoice = new Invoice();

    @Inject
	@Push(topic = "pushInvoice")
	Event<Invoice> pushEvent;

	public void create() {
		service.create(this.invoice);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Invoice Added",
						String.format("Invoice %d added successfully",
								invoice.getInvoiceNumber())));
		pushEvent.fire(invoice);
		invoice = new Invoice();
	}

}
