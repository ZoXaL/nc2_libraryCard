<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Change record</title>
		<meta charset="utf-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
		<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateForm.js"></script>
	</head>		

	<body>
		<div class="container singleTableContainer">
			<div class="panel panel-default singleTablePanel">
				<div class="panel-heading singleTablePanel-heading">
				${requestScope.panelHeader}
				</div>		

				<div class="panel-body singleTablePanel-body">
					<form id="add-editForm" method="POST">
						<div class="form-group">
							<label for="bookTitle">Book title</label>
							<input class="form-control" value="${record.title}" type="text" required name="bookTitle" placeholder="The C++ Programming Language" id="bookTitle"/>
						</div>
						<div class="form-group">
							<label for="bookAuthor">Book autor</label>
							<input class="form-control" value="${record.author}" type="text" required name="bookAuthor" placeholder="Bjarne Stroustrup" id="bookAuthor"/>
						</div>
						<div class="form-group">
							<label for="obtainDate">Obtain date</label>
							<input class="form-control dateInput" value="${record.obtainDate}" type="text" required name="obtainDate" placeholder="2016-10-16" id="obtainDate" title="Date format is yyyy-mm-dd" pattern="^\d\d\d\d-\d\d-\d\d$"/>
						</div>
						
						<div class="form-group">
							<label for="returnDate">Return date</label>
							<input class="form-control dateInput" value="${record.returnDate}" type="text" required name="returnDate" placeholder="2017-05-16" id="returnDate" title="Date format is yyyy-mm-dd" pattern="^\d\d\d\d-\d\d-\d\d$"/>
						</div>
						<input class="form-control" value="${record.title}" type="hidden" name="previousTitle" />
						
					</form>
				</div>

				<div class="panel-footer singleTablePanel-footer">
					<div class="row">
						<div class="col-sm-6">
							<input type="submit" class="btn pull-left" value="Save" form="add-editForm">
						</div>

						<div class="col-sm-6">
							<a href="${pageContext.request.contextPath}/app"><button class="btn pull-right">Back</button></a>
						</div>
					</div>						
				</div>
			</div>
		</div>
	</body>
</html>