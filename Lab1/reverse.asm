#Alex Painter
#Section: 5

#Java interpretation:
#int reverse(int num){
#	int mask = 1;
#	int reversed = 0;
#	for(int i=0; i<32; i++){
#		reversed<<1;
#		if((num & mask) != 0){
#			reversed |= 1;
#		} else {
#			reversed |= 0;
#		}
#		mask<<1;
#	}
#}

#global declarations
.globl welcome 
.globl prompt_num
.globl result

#Data
.data

welcome: 
	.asciiz "This program reverses the bits of a number\n\n" 	#46

prompt_num:
	.asciiz "Enter a 32-bit number: "							#23

result:
	.asciiz "\nReversed integer value: "						#27
	 
#Text Area (i.e. instructions)
.text

main:
	#load welcome message
	ori     $v0, $0, 4
	lui     $a0, 0x1001
	syscall	
	
	#prompt user to enter number
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x2D
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	#clear register and store number
	ori     $s0, $0, 0
	addu    $s0, $v0, $s0

	#logic
	#initialize register for mask, reveresed number, and loop counter
	addiu	$s1, $s1, 1
	ori     $s2, $0, 0
	ori     $s3, $0, 0
	#initialize registers for max loop count and constant 1
	addiu	$s4, $s4, 32
	addiu	$s5, $s5, 1

loop:
	and     $s6, $s0, $s1
	beq		$s6, $0, increment
	#only run this line if a bit on the output has to be set
	or		$s2, $s2, $s5

increment:
	#shift mask and increment loop counter
	sll     $s1, $s1, 1
	addiu   $s3, $s3, 1

	#exit if loop counter reaches termination condition
	beq		$s3, $s4, exit 
	
	#shift reversed number
	sll     $s2, $s2, 1
	j loop

exit:
	#display results text to user
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x45
	syscall

	#print result
	ori     $v0, $0, 1
	add 	$a0, $s2, $0
	syscall	


	# Exit (load 10 into $v0)
	ori     $v0, $0, 10
	syscall

