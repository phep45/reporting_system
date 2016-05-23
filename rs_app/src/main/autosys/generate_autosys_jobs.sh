#!/usr/bin/env bash

#creates jobs based on properties/* and templates/* files

PROP_FILES=properties/*
TEMPLATE_FILES=templates/*

for p in $PROP_FILES
do
	for t in $TEMPLATE_FILES
	do
		`./job_gen.sh $p $t`
	done
done

