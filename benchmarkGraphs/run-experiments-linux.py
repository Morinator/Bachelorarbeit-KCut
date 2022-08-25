#!/usr/bin/env python

#run the experiments for all relevant values of k and one specific choice of algorithm

from __future__ import print_function
from subprocess import call

import sys
import random
import os
import argparse
import glob
import subprocess as sp
import multiprocessing as mp

parser = argparse.ArgumentParser(description='A script for running our experiments on the real-world and random graphs.')


time_limit = 3600000
#time_limit = 180000
 
def work(in_file):
    sp.call(["java", "-Djava.library.path=/opt/ibm/ILOG/CPLEX_Studio201/cplex/bin/x86-64_linux/", "-jar", "ba.jar", in_file.strip()])
    return 0
 
if __name__ == '__main__':
    files = []
    #experiments for real-world instances 
    #for line in open("data_list-all-missing.txt"):
    for line in open("data_list-all.txt"):
        if not line.startswith("#"):
            files += [line.strip()]
    
    #Set up the parallel task pool to use all available processors
    count = 12
    # shuffle such that load on each processor is more evenly distributed
    random.shuffle(files)
    pool = mp.Pool(processes=count)

    #Run the jobs
	
    pool.map(work, files)


