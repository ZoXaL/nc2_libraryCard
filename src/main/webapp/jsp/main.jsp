<!-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> -->
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>libraryCard app</title>
		<meta charset="utf-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" href="css/common.css">
		<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="js/selectRow.js"></script>
		<script type="text/javascript" src="js/routing.js"></script>
	</head>
	<body>
		<div class="container singleTableContainer">
			<div class="panel panel-default singleTablePanel">

				<div class="panel-heading singleTablePanel-heading">
					Library card
				</div>

				<div class="panel-body singleTablePanel-body">
					<table class="table table-bordered table-hover" id="recordsTable">

						<thead>
							<th>id</th>
							<th>title</th>
							<th>author</th>
							<th>obtain date</th>
							<th>return date</th>
						</thead>

						<tbody>
						<c:forEach items="${requestScope.records}" var="record">
								<tr>
									<td>${record.id}</td>
									<td>${record.title}</td>
									<td>${record.author}</td>
									<td>${record.obtainDate}</td>
									<td>${record.returnDate}</td>
								</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="panel-footer singleTablePanel-footer">
					<div class="row">
					<div class="col-sm-6">
					<a href="app/add"><button class="btn pull-left">Add</button></a> 
					</div>
					<div class="col-sm-6">
						<form style="display:none" method="post" action="${pageContext.request.contextPath}/app/delete" id="hiddenDeleteForm">
							<input type="hidden" name="deleteTitle" value="none">
						</form>
						<input type="submit" class="btn pull-right" value="Delete" form="hiddenDeleteForm"><!-- 
						<button class="btn pull-right" id="deleteBtn">Delete</button> -->
						<button class="btn pull-right" id="editBtn">Edit</button>
					</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
