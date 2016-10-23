/*
Name: 			Tables / Advanced - Examples
Written by: 	Okler Themes - (http://www.okler.net)
Theme Version: 	1.5.2
*/

(function($) {

	'use strict';

	var datatableInit = function() {
		var $table = $('#datatable-details');

		// format function for row details
		var fnFormatDetails = function( datatable, tr ) {
			var data = datatable.fnGetData( tr );

			return [
				'<table class="table mb-none">',
					'<tr class="b-top-none">',
						'<td class="col-lg-2 col-xs-2" rowspan="2"><label class="mb-none"><img src=' + data[7] + '></label></td>',
						'<td class="col-lg-3 col-xs-2"><a href="' + data[9] + '">个人主页</a></td>',
						'<td class="col-lg-3 col-xs-2"><a href="' + data[8] + '">DISQUS主页</a></td>',
						'<td class="col-lg-3 col-xs-2"><a href="' + data[11] + '">评论位置</a></td>',
					'</tr>',
					'<tr>',
						'<td>邮箱： <span>' + data[12] + '</span> </td>',
						'<td>点赞次数： <span>' + data[10] + '</span></td>',
						'<td>IP记录： <span>' + data[13] + '</span> </td>',
					'</tr>',
				'</table>'
			].join('');
		};

		// insert the expand/collapse column
		var th = document.createElement( 'th' );
		var td = document.createElement( 'td' );
		td.innerHTML = '<i data-toggle class="fa fa-plus-square-o text-primary h5 m-none" style="cursor: pointer;"></i>';
		td.className = "text-center";

		$table
			.find( 'thead tr' ).each(function() {
				this.insertBefore( th, this.childNodes[0] );
			});

		$table
			.find( 'tbody tr' ).each(function() {
				this.insertBefore(  td.cloneNode( true ), this.childNodes[0] );
			});

		// initialize
		datatable = $table.dataTable({
			aoColumnDefs: [{
				bSortable: false,
				aTargets: [ 0 ]
			}],
			aaSorting: [
				[1, 'asc']
			]
		});
		// console.dir(datatable);
		// datatable.fnFilter( "允许", 5, false, true, false);

		// add a listener
		$table.on('click', 'i[data-toggle]', function() {
			var $this = $(this),
				tr = $(this).closest( 'tr' ).get(0);

			if ( datatable.fnIsOpen(tr) ) {
				$this.removeClass( 'fa-minus-square-o' ).addClass( 'fa-plus-square-o' );
				datatable.fnClose( tr );
			} else {
				$this.removeClass( 'fa-plus-square-o' ).addClass( 'fa-minus-square-o' );
				datatable.fnOpen( tr, fnFormatDetails( datatable, tr), 'details' );
			}
		});
	};

	$(function() {
		datatableInit();
	});

}).apply(this, [jQuery]);