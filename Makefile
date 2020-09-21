# Assignment 4 Makefile
# Ryan Anderson
# 28 September 2019

SRCDIR = src
BINDIR = bin
DOCDIR = doc

JAVAC = /usr/bin/javac
.SUFFIXES: .java .class

src_files=$(wildcard $(SRCDIR)/*.java)
out_files=$(src_files:$(SRCDIR)/%.java=$(BINDIR)/%.class)

compiler_flags=-d $(BINDIR) -sourcepath $(SRCDIR)

build: $(out_files)

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) $(compiler_flags) $^

docs:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

clean:
	rm $(BINDIR)/*
	rm -r $(DOCDIR)/*
