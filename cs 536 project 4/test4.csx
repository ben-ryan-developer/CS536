## test4.assignment ##

class Test {
  	int aaa;
  	char s;
  	bool bbb;
	const c = 3;

	int ia1[5];
	char ca1[5];
	bool ba1[5];

	int ia2[4];
	char ca2[8];
	bool ba2[3];

	int f1(int p1, bool p2, char p3) {
		aaa = p1;
		p1 = aaa;
		bbb = p2;
		p2 = bbb;
		s = p3;
		p3 = s;

		p1 = ia1;	## type mismatch ##
		p2 = ba1;	## type mismatch ##
		p3 = ca1;	## type mismatch ##
		ia1 = p1;	## type mismatch ##
		ba2 = p2; 	## type mismatch ##
		ca1 = p3;	## type mismatch ##

		return p1;
	}

	bool f2(int p1[], bool p2[], char p3[]) {
		p1 = ia1;
		ia1 = p1;
		p2 = ba1;
		ba2 = p2;
		p3 = ca1;
		p3 = "aaa";
		ca1 = p3;

		aaa = p1;	## type mismatch ##
		p1 = aaa;	## type mismatch ##
		bbb = p2;	## type mismatch ##
		p2 = bbb;	## type mismatch ##
		s = p3;	## type mismatch ##
		p3 = s;	## type mismatch ##
		return true;
	}

	char f3() {
		return 's';
	}

	void main() {
		c = 8;	## assignment to const ##

  		aaa = 1;
  		aaa = true;	## type mismatch ##
  		aaa = "foo";	## type mismatch ##
  		aaa = aaa;
  		aaa = bbb;	## type mismatch ##
  		aaa = s;	## type mismatch ##
		aaa = ia1;	## type mismatch ##
		aaa = f1;	## type mismatch ##

		ia1 = ia1;
		ia1 = ia2;	## different array size ##
		ia1 = f1;	## type mismatch ##

  		bbb = 1;	## type mismatch ##
  		bbb = true;
  		bbb = "foo";## type mismatch ##	
  		bbb = aaa;	## type mismatch ##
  		bbb = bbb;
  		bbb = s;	## type mismatch ##
		bbb = ba1;	## type mismatch ##
		bbb = f2;	## type mismatch ##

		ba1 = ba1;
		ba1 = ba2;	## different array size ##
		ba1 = f2;	## type mismatch ##

  		s = 1;	## type mismatch ##
  		s = true;	## type mismatch ##
  		s = "foo";	## type mismatch ##
  		s = aaa;	## type mismatch ##
  		s = bbb;	## type mismatch ##
  		s = s;
		s = ca1;	## type mismatch ##
		s = '3';
		s = f3;	## type mismatch ##

		ca1 = ca1;
		ca1 = ca2;	## different array size ##
		ca1 = "hello";
		ca1 = "bad";	## different array size ##
		ca1 = "bad\\";	## different array size ##
		ca1 = "good\\";
		ca1 = f3;	## type mismatch ##

		f1 = aaa;	## type mismatch ##
		f1 = ia1;	## type mismatch ##
		f1 = f1;	## type mismatch ##
		f2 = bbb;	## type mismatch ##
		f2 = ba1;	## type mismatch ##
		f3 = s;		## type mismatch ##
		f3 = ca1;	## type mismatch ##
		f3 = main;	## type mismatch ##

		ia1 = ca1;	## type mismatch ##
		ca1 = ba1;	## type mismatch ##
		ba1 = ia1;	## type mismatch ##

  		aaa = main;	## type mismatch ##
  		main = 0;	## type mismatch ##
  		main = main;	## type mismatch ##
		ia2 = main;	## type mismatch ##
		main = ba2;	## type mismatch ##
		main = f1;	## type mismatch ##
		main = f2;	## type mismatch ##
		main = f3;	## type mismatch ##

		L: while (true) {
			continue L;
		}

		L = aaa;	## type mismatch ##
		bbb = L;	## type mismatch ##
		L = main;	## type mismatch ##
		main = L;	## type mismatch ##
		L = L;	## type mismatch ##		
	}

}
