;;;; -*- Clojure; encoding=utf-8; -*-
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;   File Name: profile.clj
;;;; Description: Provisioning profile data types
;;;;     Created: <Mon Apr  1 12:13:24 MST 2024 tammy>
;;;; Last Update: Time-Stamp: <>
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(ns machinecfg.profile
  (:gen-class)
  (:require [miner.tagged :as tag]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Define the data structures for provisioning profiles.
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defrecord Condition [name enabled? check-type check-field check-operator
                      check-value if-true if-false]
  Object
  (print-method [this w] (tag/pr-tagged-record-on this w)))

(defrecord Action [name enabled? action-type action-args]
  Object
  (print-method [this w] (tag/pr-tagged-record-on this w)))

(defrecord Profile [name enabled? host-name connect-via system-type
                    os-type preconditions actions postconditions]
  Object
  (print-method [this w] (tag/pr-tagged-record-on this w)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helper functions to create the data types.
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; -------------------------------
;; Create a provisioning condtion 
;; -------------------------------

(defn make-condition
  "Creates a new provisioning condition."

  [c-name c-enabled? c-type c-field c-operator c-value c-iftrue c-iffalse]
  (Condition. c-name c-enabled? c-type c-field c-operator c-value
              c-iftrue c-iffalse)

  [c-name c-type c-field c-value c-iftrue c-iffalse]
  (Condition. c-name true c-type c-field :equal c-value c-iftrue c-iffalse)

  [c-name c-type c-field c-value]
  (Condition. c-name true c-type c-field :equal c-value :succeed :fail))

;; ----------------------------
;; Create a provisioning action
;; ----------------------------

(defn make-action
  "Creates a new provisioning action."

  [a-name a-enabled? a-type a-args]
  (Action. a-name a-enabled? a-type a-args)

  [a-name a-type a-args]
  (Action. a-name true a-type a-args)

  [a-name a-type]
  (Action. a-name true a-type nil))

;; ------------------------------
;; Create a provisioning profile
;; ------------------------------

(defn make-profile
  "Creates a new provisioning profile."

  [p-name p-enabled? p-hostname p-connect-via p-system-type p-os-type
   p-preconditions p-actions p-postconditions]
  (Profile. p-name p-enabled? p-hostname p-connect-via p-system-type
            p-os-type p-preconditions p-actions p-postconditions)

  [p-name p-hostname p-connect-via p-system-type p-os-type
   p-preconditions p-actions p-postconditions]
  (Profile. p-name true p-hostname p-connect-via p-system-type
            p-os-type p-preconditions p-actions p-postconditions)

  [p-name p-hostname p-system-type p-os-type p-preconditions
   p-actions p-postconditions]
  (Profile. p-name true p-hostname :ssh p-system-type p-os-type
            p-preconditions p-actions p-postconditions)

  [p-name p-hostname p-system-type p-os-type p-actions]
  (Profile. p-name true p-hostname :ssh p-system-type
            p-os-type nil p-actions nil)

  [p-name p-hostname p-actions]
  (Profile. p-name true p-hostname :ssh :linux :debian
            nil p-actions nil))

;;-------------------------
;; Profile File I/O Helpers
;;-------------------------

(defn file->profile
  "Load a provisioning profile from a file."
  [file-path])

(defn profile->file
  "Save a provisioning profile to a file."
  [profile file-path])
