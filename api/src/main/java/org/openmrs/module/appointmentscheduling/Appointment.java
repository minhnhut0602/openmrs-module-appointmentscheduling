/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.appointmentscheduling;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.module.appointmentscheduling.serialize.AppointmentStatusSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or
 * {@link BaseOpenmrsMetadata}.
 */
public class Appointment extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TODO confirm that "WALK-IN" should be considered active
    @JsonSerialize(using = AppointmentStatusSerializer.class)
	public enum AppointmentStatus {
		SCHEDULED("Scheduled", false, false), RESCHEDULED("Rescheduled", false, false), WALKIN("Walk-In", false, true), CANCELLED(
		        "Cancelled", true, false), WAITING("Waiting", false, true), INCONSULTATION("In-Consultation", false, true), COMPLETED(
		        "Completed", false, false), MISSED("Missed", false, false), CANCELLED_AND_NEEDS_RESCHEDULE(
		        "Cancelled and Needs Reschedule", true, false);

		private final String name;
		
		/**
		 * Whether or not an appointment with this status should be considered "cancelled" Cancelled
		 * statuses: CANCELLED, CANCELLED_AND_NEEDS_RESCHEDULE
		 */
		private boolean cancelled;
		
		/**
		 * Whether or not an appointment with this status is an "active" appointment, where
		 * active=patient checked-in and present within the health facility Active statuses: WALKIN,
		 * WAITING, INCONSULTATION
		 */
		private boolean active;
		
		private AppointmentStatus(final String name, final boolean cancelled, final boolean active) {
			this.name = name;
			this.cancelled = cancelled;
			this.active = active;
		}

		public String getName() {
			return this.name;
		}
		public boolean isCancelled() {
			return this.cancelled;
		}
		public boolean isActive() {
			return this.active;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public static List<AppointmentStatus> filter(Predicate predicate) {
			List<AppointmentStatus> appointmentStatuses = new ArrayList(Arrays.asList(AppointmentStatus.values())); // need to assign to a new array because Arrays.asList is fixed length
			CollectionUtils.filter(appointmentStatuses, predicate);
			return appointmentStatuses;
		}
		
		public static Predicate cancelledPredicate = new Predicate() {
			
			@Override
			public boolean evaluate(Object o) {
				return ((AppointmentStatus) o).isCancelled();
			}
		};
		
		public static Predicate notCancelledPredicate = new Predicate() {
			
			@Override
			public boolean evaluate(Object o) {
				return !((AppointmentStatus) o).isCancelled();
			}
		};
		
	}
	
	private Integer appointmentId;
	
	private TimeSlot timeSlot;
	
	private Visit visit;
	
	private Patient patient;
	
	private AppointmentStatus status;
	
	private String reason;
	
	private String cancelReason;
	
	private AppointmentType appointmentType;
	
	public Appointment() {
		
	}
	
	public Appointment(Integer appointmentId) {
		setId(appointmentId);
	}
	
	public Appointment(TimeSlot timeSlot, Visit visit, Patient patient, AppointmentType appointmentType,
	    AppointmentStatus status) {
		setTimeSlot(timeSlot);
		setVisit(visit);
		setPatient(patient);
		setStatus(status);
		setAppointmentType(appointmentType);
	}
	
	public Integer getAppointmentId() {
		return appointmentId;
	}
	
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	/**
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	@Override
	public Integer getId() {
		return getAppointmentId();
	}
	
	/**
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer id) {
		setAppointmentId(id);
	}
	
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
	
	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public Visit getVisit() {
		return visit;
	}
	
	public void setVisit(Visit visit) {
		this.visit = visit;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public AppointmentStatus getStatus() {
		return status;
	}
	
	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}
	
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	public AppointmentType getAppointmentType() {
		return appointmentType;
	}
	
	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
	
}
