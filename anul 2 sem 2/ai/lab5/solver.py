# -*- coding: utf-8 -*-
"""
In this file your task is to write the solver function!

"""


class FuzzySet:
    def __init__(self, a, b, c):
        self.a = a
        self.b = b
        self.c = c

    def get_membership_degree(self, x):
        return max(0, min((x - self.a) / (self.b - self.a), 1, (self.c - x) / (self.c - self.b)))


def get_theta_functions():
    return {
        "NVB": FuzzySet(-50, -40, -30),
        "NB": FuzzySet(-40, -25, -10),
        "N": FuzzySet(-20, -10, 0),
        "ZO": FuzzySet(-5, 0, 5),
        "P": FuzzySet(0, 10, 20),
        "PB": FuzzySet(10, 25, 40),
        "PVB": FuzzySet(25, 40, 50)
    }


def get_theta_membership(x):
    theta_functions = get_theta_functions()
    theta_membership = {}
    for thetaKey in theta_functions.keys():
        theta_membership[thetaKey] = theta_functions[thetaKey].get_membership_degree(x)
    return theta_membership


def get_omega_functions():
    return {
        "NB": FuzzySet(-10, -8, -3),
        "N": FuzzySet(-6, -3, 0),
        "ZO": FuzzySet(-1, 0, 1),
        "P": FuzzySet(0, 3, 6),
        "PB": FuzzySet(3, 8, 10)
    }


def get_omega_membership(x):
    omega_functions = get_omega_functions()
    omega_membership = {}
    for omegaKey in omega_functions.keys():
        omega_membership[omegaKey] = omega_functions[omegaKey].get_membership_degree(x)
    return omega_membership


def get_traction_force_functions():
    return {
        "NVVB": FuzzySet(-50, -32, -24),
        "NVB": FuzzySet(-32, -24, -16),
        "NB": FuzzySet(-24, -16, -8),
        "N": FuzzySet(-16, -8, 0),
        "Z": FuzzySet(-8, 0, 8),
        "P": FuzzySet(0, 8, 16),
        "PB": FuzzySet(8, 16, 24),
        "PVB": FuzzySet(16, 24, 32),
        "PVVB": FuzzySet(24, 32, 50)
    }


def get_rule_table():
    return {
        "PVB PB": "PVVB", "PVB P": "PVVB", "PVB ZO": "PVB", "PVB N": "PB", "PVB NB": "P",
        "PB PB": "PVVB", "PB P": "PVB", "PB ZO": "PB", "PB N": "P", "PB NB": "Z",
        "P PB": "PVB", "P P": "PB", "P ZO": "P", "P N": "Z", "P NB": "N",
        "ZO PB": "PB", "ZO P": "P", "ZO ZO": "Z", "ZO N": "N", "ZO NB": "NB",
        "N PB": "P", "N P": "Z", "N ZO": "N", "N N": "NB", "N NB": "NVB",
        "NB PB": "Z", "NB P": "N", "NB ZO": "NB", "NB N": "NVB", "NB NB": "NVVB",
        "NVB PB": "N", "NVB P": "NB", "NVB ZO": "NVB", "NVB N": "NVVB", "NVB NB": "NVVB"
    }


def solver(t, w):
    """
    Parameters
    ----------
    t : TYPE: float
        DESCRIPTION: the angle theta
    w : TYPE: float
        DESCRIPTION: the angular speed omega

    Returns
    -------
    F : TYPE: float
        DESCRIPTION: the force that must be applied to the cart
    or
    None :if we have a division by zero
    """
    theta_functions = get_theta_functions()
    omega_functions = get_omega_functions()

    theta_membership = get_theta_membership(t)
    omega_memberships = get_omega_membership(w)
    rule_table = get_rule_table()
    forces = {}
    for theta_key in theta_functions.keys():
        for omega_key in omega_functions.keys():
            force_value = min(theta_membership[theta_key], omega_memberships[omega_key])
            force_key = theta_key + " " + omega_key
            if rule_table[force_key] not in forces:
                forces[rule_table[force_key]] = force_value
            elif forces[rule_table[force_key]] < force_value:
                forces[rule_table[force_key]] = force_value

    products = {"NVVB": -32, "NVB": -24, "NB": -16, "N": -8, "Z": 0, "P": 8, "PB": 16, "PVB": 24, "PVVB": 32}
    sum = 0
    weighted_sum = 0
    for force_key in forces.keys():
        sum += forces[force_key]
        weighted_sum += forces[force_key] * products[force_key]
    return weighted_sum/sum if sum != 0 else None
