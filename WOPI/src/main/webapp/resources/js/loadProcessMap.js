/**
 * bpmn-js-seed - async
 * 
 * This is an example script that loads a bpmn diagram <diagram.bpmn> and opens
 * it using the bpmn-js viewer.
 * 
 * YOU NEED TO SERVE THIS FOLDER VIA A WEB SERVER (i.e. Apache) FOR THE EXAMPLE
 * TO WORK. The reason for this is that most modern web browsers do not allow
 * AJAX requests ($.get and the like) of file system resources.
 */

// var SchedulePlanned_CN=[3,2,15,15,45,2];
var steps = [ "Requirement Definition and Analysis", "Quotation", "CI Ordering", "PUI-Carrier Ordering", "WAN Implementation", "WAN Delivery" ]
var stepsID = [ "ReqConsolidation", "Quotation", "CIOrder", "CPOrder", "WANImplementation", "WANDelivery" ];

var SchedulePlanned_SEA = [ 3, 33, 17, 12, 60, 4 ];
var SchedulePlanned_CN = [ 3, 2, 15, 15, 45, 2 ];
var SchedulePlanned_IN = [ 3, 30, 7, 15, 60, 5 ];
var SchedulePlanned_JP = [ 3, 18, 15, 3, 42, 2 ];
var SchedulePlanned_KR = [ 3, 6, 5, 10, 20, 2 ];
var SchedulePlanned_ANZ = [ 3, 33, 17, 2, 45, 4 ];

var currentSchedule;

function importXML(xml) {

	var BpmnViewer = window.BpmnJS;

	var bpmnViewer = new BpmnViewer({
		container : '#canvas'
	});

	// import diagram
	bpmnViewer.importXML(xml, function(err) {
		if (err) {
			return console.error('could not import BPMN 2.0 diagram', err);
		}

		var canvas = bpmnViewer.get('canvas'), overlays = bpmnViewer.get('overlays');

		// zoom to fit full viewport
		canvas.zoom('fit-viewport');

		defineScheduleBasedOnCountry();

		for (var i = 0; i < steps.length; i++) {
			addPlannedDaysInView(overlays, stepsID[i], currentSchedule[i]);
		}

		var requestDate = new Date(getISODateString(requesetDate));
		var workingdaysRequesToNow = workingDaysBetweenDates(requestDate, new Date());

		var stepIndex = steps.indexOf(stage);
		var planedDaysUntilCurrentStage = 0
		for (var i = 0; i <= stepIndex; i++) {
			planedDaysUntilCurrentStage += currentSchedule[i] * 1;
		}

		document.getElementById("connectionNumber").innerHTML = connectionNr;
		document.getElementById("planWorkingDaysToNow").innerHTML = planedDaysUntilCurrentStage;
		document.getElementById("requestDateValue").innerHTML = requesetDate;
		document.getElementById("workingDaysUntilNow").innerHTML = workingdaysRequesToNow;
		document.getElementById("plannedInstalationDate").innerHTML = plannedInstallationDate;

		/*
		 * if (workingdaysRequesToNow < (0.9) * planedDaysUntilCurrentStage) { //
		 * less 90% of the target
		 * canvas.addMarker(stepsID[steps.indexOf(stage)],
		 * 'currentStatus_green'); } else if (workingdaysRequesToNow > (1.1) *
		 * planedDaysUntilCurrentStage) { // more than 110% of the target
		 * canvas.addMarker(stepsID[steps.indexOf(stage)], 'currentStatus_red'); }
		 * else { // between 90~110% of the target
		 * canvas.addMarker(stepsID[steps.indexOf(stage)],
		 * 'currentStatus_yellow'); }
		 * 
		 */
		canvas.addMarker(stepsID[steps.indexOf(stage)], 'currentStatus');

		if (workingdaysRequesToNow < 0.9 * planedDaysUntilCurrentStage) {
			document.getElementById("bar").className = "OKStatus";
		} else if (workingdaysRequesToNow > 1.1 * planedDaysUntilCurrentStage) {
			document.getElementById("bar").className = "BreachStatus";
		} else {
			document.getElementById("bar").className = "WarnStatus";
		}

	});

}

function defineScheduleBasedOnCountry() {
	if (countryCode == "PH" || countryCode == "ID" || countryCode == "SG" || countryCode == "MY" || countryCode == "TH" || countryCode == "VN") {
		currentSchedule = SchedulePlanned_SEA;
	}
	if (countryCode == "JP") {
		currentSchedule = SchedulePlanned_JP;
	}
	if (countryCode == "CN") {
		currentSchedule = SchedulePlanned_CN;
	}

	if (countryCode == "AU" || countryCode == "NZ") {
		currentSchedule = SchedulePlanned_ANZ;
	}

	if (countryCode == "KR") {
		currentSchedule = SchedulePlanned_KR;
	}

}

function loadProcessMap() {

	document.getElementById("canvas").innerHTML = "";
	$.get('../resources/process/WOPI.bpmn', importXML, 'text');

}

function getISODateString(dateString) {
	// due to the excel format issue, it could be 09-Feb-2015, or 30/10/2015
	if (dateString.indexOf("/") != "-1") {
		var dateStringArray = dateString.split("-");
	}

	if (dateString.indexOf("-") != "-1") {
		var dateStringArray = dateString.split("-");
	}

	return dateStringArray[2] + "," + dateStringArray[1] + "," + dateStringArray[0];

}

function addPlannedDaysInView(overlays, stageID, days) {
	overlays.add(stageID, 'note', {
		position : {
			bottom : 70,
			right : 19
		},
		html : '<div class="diagram-note">' + days + '</div>'
	});
}

function workingDaysBetweenDates(startDate, endDate) {

	// Validate input
	if (endDate < startDate)
		return 0;

	// Calculate days between dates
	var millisecondsPerDay = 86400 * 1000; // Day in milliseconds
	startDate.setHours(0, 0, 0, 1); // Start just after midnight
	endDate.setHours(23, 59, 59, 999); // End just before midnight
	var diff = endDate - startDate; // Milliseconds between datetime objects
	var days = Math.ceil(diff / millisecondsPerDay);

	// Subtract two weekend days for every week in between
	var weeks = Math.floor(days / 7);
	days = days - (weeks * 2);

	// Handle special cases
	var startDay = startDate.getDay();
	var endDay = endDate.getDay();

	// Remove weekend not previously removed.
	if (startDay - endDay > 1)
		days = days - 2;

	// Remove start day if span starts on Sunday but ends before Saturday
	if (startDay == 0 && endDay != 6)
		days = days - 1

		// Remove end day if span ends on Saturday but starts after Sunday
	if (endDay == 6 && startDay != 0)
		days = days - 1

	return days;
}

// Request consolidation
// Quotation
// CI internal ordering
// CP internal ordering
// WAN implementation
// WAN delivery

// VN
// MY
// ID
// PH

// NZ
// AU
