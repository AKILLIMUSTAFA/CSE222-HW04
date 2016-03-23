li 	$t0, 4

li 	$t1, 3

li 	$t2, 12

li 	$t3, 3
mult 	$t1,$t3
mflo 	$t4
sub 	$t5,$t0,$t4

move 	$t1,$t5

li 	$t3, 3
div 	$t0,$t3
mflo 	$t4
mult 	$t4,$t1
mflo 	$t5
li 	$t6, 21
add 	$t7,$t5,$t6

move 	$t2,$t7

move 	$a0, $t2
li 	$v0, 16
syscall
